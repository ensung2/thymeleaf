package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j  // log 생성시 사용
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    /* 컨트롤러(FormItemController)에 어떤게 호출이 되든 자동으로 해당 리스트가 모델에 다 담기게 됨 */
    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String ,String> regions= new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;

        /* model.addAttribute("regions",rigions); 가 자동으로 추가됨 */
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        // 해당 ENUM의 모든 정보를 배열로 사용한다(ENUM의 특징)
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("Fast", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("Normal", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("Slow", "느린 배송"));

        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "form/item";
    }

    @GetMapping("/add") // 등록 폼
    public String addForm(Model model) {
        /* 이슈 해결(Neither BindingResult nor plain target object for bean name)
            : 처음 입력 폼 조회 시 입력 폼은 모두 비워져 있어야 하기 때문에 빈 객체(new item())을 전달 */
        model.addAttribute("item", new Item());

        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        log.info("item.open={}", item.getOpen());   //item.open=true
        log.info("item.regions={}", item.getRegions());   //item.regions=[BUSAN, JEJU] (선택한 지역 출력, 선택 안하면 공백 출력)
        log.info("item.itemTypes={}", item.getItemType());
        log.info("item.delivery={}", item.getDeliveryCode());
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }

    /*==========================================================================*/

}

